const boardId = id.value;

function deleteBoard(id){
    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            url: '/api/boards/' + id
            , type: 'DELETE'
            , success: function (result) {
                alert('삭제됨.');
                window.location.href = '/board/list';
            }
            , error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}

function heartOut(id){
    $.ajax({
        url: '/board/heart/' + id
        , type: 'POST'
        , dataType: 'JSON'
        , success: function (result) {
            $('#heartCnt').text(result.heartCount);
            if(result.heartUser){
                $('#heart').text('❤');
            }else{
                $('#heart').text('🤍');
            }
        }
        , error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
}

function doHeart(id){
    $.ajax({
        url: '/api/doHeart/' + id
        // url: '/board/doHeart/' + id
        , type: 'POST'
        // , dataType: 'JSON'
        , success: function (result) {
            heartOut(id);
        }
        , error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
}

function replyEditor(reply){
    reply.getElementsByClassName("reply-content")[0].readOnly = false;

    var replyEditButton = document.createElement("a");

    reply.getElementsByClassName("reply-edit")[0].innerText = "";

    replyEditButton.innerText = "변경완료";
    replyEditButton.href = "javascript:;";
    replyEditButton.onclick = (function(index) {
        return function() {

            let replyContent = reply.getElementsByClassName("reply-content")[0].value;
            let POST = [];

            POST = {
                id : reply.Id
                , replyContent : replyContent
            }

            $.ajax({
                // url: '/board/doReply/' + id
                url: '/api/doReply/' + boardId
                // url: '/board/doHeart/' + id
                , type: 'POST'
                , data: POST
                // , dataType: 'JSON'
                , success: function (result) {
                    if(result === "success"){
                        heartOut(boardId);
                        replyOut(boardId);
                    }else{
                        $('#reply-error').text(result);
                    }
                }
                , error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            });
        };
    }(reply));
    reply.getElementsByClassName("reply-edit")[0].append(replyEditButton);

}

function replyDeletor(replyId){
    if(confirm("댓글을 삭제하시겠습니까?")){
        $.ajax({
            url: '/api/boards/deleteReply/' + replyId
            , type: 'DELETE'
            , success: function (result) {
                // 댓글 삭제 이후 필요한 부분만 가져와준다. 새로고침은 할필요 없이 댓글과 좋아요만 가져오기
                heartOut(boardId);
                replyOut(boardId);
            }
            , error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}

function replyOut(id){

    $.ajax({
        url: '/api/outReply/' + id
        // url: '/board/doHeart/' + id
        , type: 'GET'
        , dataType: 'JSON'
        , success: function (result) {
            $('#reply-out *').remove();     // replyout함수가 불릴때마다 나타나있는 댓글 초기화하기

            let replyTable = document.createElement("table");
            replyTable.className = "table table-bordered";

            for(var i=0; i<result.length; i++){
                var tr = document.createElement("tr");
                tr.Id = result[i].id;
                var replyUserName = document.createElement("td");
                var replyContentContain = document.createElement("td");
                var replyContent = document.createElement("textarea");
                var replyEdit = document.createElement("td");
                replyUserName.className = "reply-user";
                replyContent.className = "reply-content";
                replyContent.readOnly = true;
                replyEdit.className = "reply-edit";

                // 댓글 작성자 tr에 넣기
                replyUserName.innerText = (result[i].userName);
                tr.append(replyUserName);

                // 댓글 내용 넣기
                replyContent.innerText = (result[i].replyContent);
                replyContentContain.append(replyContent);
                tr.append(replyContentContain);

                // 댓글 수정/삭제 기능 부여하기
                if(result[i].checkUser){
                    var replyEditA = document.createElement("a");
                    var replyDeleteA = document.createElement("a");
                    replyEditA.innerText = "수정";
                    replyEditA.href = 'javascript:;';
                    replyEditA.onclick = (function(index) {
                        return function() {
                            replyEditor(index);
                        };
                    }(tr));
                    replyDeleteA.innerText = "삭제";
                    replyDeleteA.href = 'javascript:;';
                    replyDeleteA.onclick = (function(index) {
                        return function() {
                            replyDeletor(index);
                        };
                    }(result[i].id));
                    replyEdit.append(replyEditA);
                    replyEdit.append(replyDeleteA);
                }
                tr.append(replyEdit);
                replyTable.append(tr);
            }
            $('#reply-out').append(replyTable);
        }
        , error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
}

function doReply(id){
    let replyContent = document.getElementById("replyContent").value;
    let POST = [];

    POST = {
        replyContent : replyContent
    }

    $.ajax({
        // url: '/board/doReply/' + id
        url: '/api/doReply/' + id
        // url: '/board/doHeart/' + id
        , type: 'POST'
        , data: POST
        // , dataType: 'JSON'
        , success: function (result) {
            if(result === "success"){
                document.getElementById("replyContent").value='';
                heartOut(id);
                replyOut(id);
            }else{
                $('#reply-error').text(result);
            }
        }
        , error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
}
function init(){

    // 좋아요 / 좋아요 취소
    document.getElementById("heart").addEventListener(
        "click", event=>doHeart(boardId)
    )

    // 좋아요 정보 받아오기
    heartOut(boardId);

    // 댓글 받아오기
    replyOut(boardId);

    // 댓글 작성하기
    document.getElementById("post").addEventListener(
        "click", event=>doReply(boardId)
    )

}

init();
