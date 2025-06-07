console.log("Admin file loaded");

/*
when image_file_input changes, then we do the query selector and pass image id we get image object, 
on that object we can add change event Listener, means if event change then this function will execute


for preview image below code
*/

document
  .querySelector("#image_file_input")
  .addEventListener("change", function (event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      document
        .querySelector("#upload_image_preview")
        .setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);
  });
