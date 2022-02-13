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
        url: '/board/reply/' + id
        // url: '/board/doHeart/' + id
        , type: 'GET'
        // , dataType: 'JSON'
        , success: function (result) {
            heartOut(id);
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
                alert("성공");
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

    // 댓글 작성하기
    document.getElementById("post").addEventListener(
        "click", event=>doReply(id.value)
    )
}

init();