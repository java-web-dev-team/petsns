
var open = () => {
    document.querySelector(".modal").classList.remove("hidden");
}

var close = () => {
    document.querySelector(".modal").classList.add("hidden");
}


var callopen = document.querySelectorAll(".openBtn");
for (var co of callopen){
    co.addEventListener('click', open);
}

var callclose = document.querySelectorAll(".closeBtn");
for (var co of callclose){
    co.addEventListener('click', close);
}

var callclose2 = document.querySelectorAll(".bg");
for (var co of callclose2) {
    co.addEventListener('click', close);
}


