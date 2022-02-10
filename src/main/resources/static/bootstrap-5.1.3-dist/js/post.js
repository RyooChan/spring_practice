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
function like(id){
    $.ajax({
        url: '/board/heart/' + id
        , type: 'POST'
        , dataType: 'JSON'
        , success: function (result) {
            $('#heartCnt').replaceWith(result.heartCount);
            if(result.heartUser){
                $('#heart').replaceWith('❤');
            }else{
                $('#heart').replaceWith('🤍');
            }
        }
        , error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
}

function init(){
    // 글 삭제
    document.getElementById("post").addEventListener(
        "click", event=>deleteBoard(event.target)
    )

    // 좋아요 정보 받아오기
    like(id.value);
}

init();