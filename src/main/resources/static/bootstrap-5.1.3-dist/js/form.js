
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

document.getElementById("picture").addEventListener(
    "change", event=>setThumbnail(event.target)
);