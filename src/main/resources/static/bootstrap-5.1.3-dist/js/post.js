function deleteBoard(id){
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