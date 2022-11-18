var close = () => {
    var closemodal = document.querySelectorAll(".modal");
    for (var close of closemodal){
        close.classList.add("hidden");
    }
}
var callclose2 = document.querySelectorAll(".bg");
for (var co of callclose2) {
    co.addEventListener('click', close);
}

function modal_open(id){
    document.getElementById(id).classList.remove("hidden");
}

function modal_close(id){
    document.getElementById(id).classList.add("hidden");
}