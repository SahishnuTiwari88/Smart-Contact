console.log("Script file loaded");

let currentTheme = getTheme();
console.log(currentTheme);
document.addEventListener("DOMContentLoaded",()=>{
    changeTheme();
});


//todo change theme
function changeTheme() {
  //set to page
  document.querySelector("html").classList.add(currentTheme);
  //set the listener to change theme button
  const themeChange = document.querySelector("#theme_change_button");
    
  themeChange.addEventListener("click", (event) => {
    const oldTheme = currentTheme;
    console.log("change theme button clicked");
   
    if (currentTheme == "dark") {
      //change theme to light
      currentTheme = "light";
    } else {
      //change theme to dark
      currentTheme = "dark";
    }
    //theme update in local storage b/c it change above but not reflect on web so need to change on local storage
    setTheme(currentTheme);
     //remove current theme
    document.querySelector("html").classList.remove(oldTheme);
    //set current theme
    document.querySelector("html").classList.add(currentTheme);

    themeChange.querySelector("span").textContent =
    currentTheme == "light" ? "Dark" : "Light";
  });
}

//set theme to local storage it is available till refreshed or closed
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
