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
                var replyId = document.createElement("td");
                var replyUserName = document.createElement("td");
                var replyContent = document.createElement("td");
                replyUserName.innerText = (result[i].userName);
                replyContent.innerText = (result[i].replyContent);
                tr.Id = result[i].id;
                tr.append(replyUserName);
                tr.append(replyContent);
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
                $('#replyContent').text();
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
    // 글 삭제
    document.getElementById("delete").addEventListener(
        "click", event=>deleteBoard(event.target)
    )

    // 좋아요 / 좋아요 취소
    document.getElementById("heart").addEventListener(
        "click", event=>doHeart(id.value)
    )

    // 좋아요 정보 받아오기
    heartOut(id.value);

    // 댓글 받아오기
    replyOut(id.value);

    // 댓글 작성하기
    document.getElementById("post").addEventListener(
        "click", event=>doReply(id.value)
    )
}

init();