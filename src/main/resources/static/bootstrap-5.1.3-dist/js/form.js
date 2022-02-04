// 사진기능 안넣음~
// function setThumbnail(target){
//     for(var image of target.files){
//         let reader = new FileReader();
//         reader.onload = function(event){
//             let img = document.createElement("img");
//             img.setAttribute("src", event.target.result);
//             img.className = "h-100";
//             document.querySelector("#view_area").appendChild(img);
//         };
//         reader.readAsDataURL(image);
//     }
// }
//
// function removeThumbnail(target){
//     if(document.querySelector("#view_area").childElementCount <= 0) return;     // 미리보기 삭제할 요소 없으면 return
//     document.querySelector("#view_area").removeChild(target);       // 미리보기 이미지 삭제
//     target.remove();    // 파일 삭제
// }
//

let oEditors = [];

function smartEditor(){
    oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef : oEditors
        , elPlaceHolder : "content"
        , sSkinURI : "/nse_files/SmartEditor2Skin.html"
        , fCreator : "createSEditor2"
        , htParams : {
            bUseToolbar : true
            , bUseVerticalResizer : false
            , bUseModeChanger : false
        }
    });
}

function save(){
    if(confirm("글을 작성하시겠습니까?")){
        oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
        let title = document.getElementById("title").value;
        let content = document.getElementById("content").value;
        let POST = [];

        if(!isNaN(id.value)) {
            POST = {
                id : id.value
                , title: title
                , content: content
            }
        }else{
            POST = {
                title: title
                , content: content
            }
        }

        $.ajax({
            url: "/board/form"
            // url: "/api/boards/form"
            , type: 'POST'
            , data: POST
            , success: function(data){
                console.log(data);
                if(data.startsWith('<div class="container" >')){        // 아무리 해보려해도 안돼서 변경되는 부분을 찾아 하드코딩 진행함.
                    $('#form').replaceWith(data);
                    smartEditor();
                }else{
                    alert('작성 완료.');
                    window.location.href = '/board/list';
                }
            }
            , error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}


function init(){
    // 다중 파일 미리보기 함수 적용
    // document.getElementById("picture").addEventListener(
    //     "change", event=>setThumbnail(event.target)
    // );

    // 파일 클릭시 삭제하기
    // document.getElementById("view_area").addEventListener(
    //     "click", event=>removeThumbnail(event.target)
    // )

    // 스마트에디터 글 전송
    // document.getElementById("postIt").addEventListener(
    //     "click", event=>save(event.target)
    // )

    smartEditor();

    document.addEventListener('click',function(event){
        if(event.target && event.target.id === "postIt"){
            save(event.target);
        }
    });
}

init();