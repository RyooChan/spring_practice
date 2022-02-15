const boardId = id.value;

function deleteBoard(id){
    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            url: '/api/boards/' + id
            , type: 'DELETE'
            , success: function (result) {
                console.log('result', result);
                alert('삭제됨.');
                window.location.href = '/board/list';
            }
            , error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}
// function replySave(content){
// //     if(confirm("작성하시겠습니까?")){
// //         $.ajax({
// //             url: '/api/boards/' + id
// //             , type: 'DELETE'
// //             , success: function (result) {
// //                 console.log('result', result);
// //                 alert('삭제됨.');
// //                 window.location.href = '/board/list';
// //             }
// //             , error: function (request, status, error) {
// //                 alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
// //             }
// //         });
// //     }
// // }
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

function replyEditor(id){
    alert("제작예정");
}

function replyDeletor(replyId, boardId){
    if(confirm("댓글을 삭제하시겠습니까?")){
        $.ajax({
            url: '/api/boards/deleteReply/' + replyId
            , type: 'DELETE'
            , success: function (result) {
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
            $('#reply-out *').remove();

            let replyTable = document.createElement("table");
            replyTable.className = "table table-bordered";

            for(var i=0; i<result.length; i++){
                var tr = document.createElement("tr");
                tr.Id = result[i].id;
                var replyUserName = document.createElement("td");
                var replyContent = document.createElement("td");
                var replyEdit = document.createElement("td");
                replyUserName.className = "reply-user";
                replyContent.className = "reply-content";
                replyEdit.className = "reply-edit";
                if(result[i].checkUser){
                    var replyEditA = document.createElement("a");
                    var replyDeleteA = document.createElement("a");
                    replyEditA.innerText = "수정";
                    replyEditA.href = 'javascript:;';
                    // replyEditA.onclick = function() {
                    //     console.log("수정");
                    //     check(this);
                    // }
                    replyEditA.onclick = (function(index) {
                        return function() {
                            replyEditor(index, id.value);
                        };
                    }(result[i].id));
                    replyDeleteA.innerText = "삭제";
                    replyDeleteA.href = 'javascript:;';
                    // replyDeleteA.onclick = function(){
                    //     console.log("삭제");
                    //     check(this);
                    // }
                    replyDeleteA.onclick = (function(index) {
                        return function() {
                            replyDeletor(index, boardId);
                        };
                    }(result[i].id));
                    replyEdit.append(replyEditA);
                    replyEdit.append(replyDeleteA);
                }
                replyUserName.innerText = (result[i].userName);
                replyContent.innerText = (result[i].replyContent);
                tr.append(replyUserName);
                tr.append(replyContent);
                tr.append(replyEdit);
                replyTable.append(tr);
            }
            $('#reply-out').append(replyTable);
            console.log(result);
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
                $('#replyContent').text("");
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
