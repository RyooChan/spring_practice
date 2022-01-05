function setThumbnail(target){
    for(var image of target.files){
        let reader = new FileReader();
        reader.onload = function(event){
            let img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.className = "h-100";
            document.querySelector("#view_area").appendChild(img);
        };
        reader.readAsDataURL(image);
    }
}

function removeThumbnail(target){
    if(document.querySelector("#view_area").childElementCount <= 0) return;     // 미리보기 삭제할 요소 없으면 return
    document.querySelector("#view_area").removeChild(target);       // 미리보기 이미지 삭제
    target.remove();    // 파일 삭제
}

function init(){
    // 다중 파일 미리보기 함수 적용
    document.getElementById("picture").addEventListener(
        "change", event=>setThumbnail(event.target)
    );

    // 파일 클릭시 삭제하기
    document.getElementById("view_area").addEventListener(
        "click", event=>removeThumbnail(event.target)
    )
}


init();