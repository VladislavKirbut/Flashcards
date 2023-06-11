"use strict";

function onClick() {
    let btn = document.getElementsByClassName("rotate-button")[0],
        rotateCard = document.getElementsByClassName("rotate-card"),
        card = document.getElementsByClassName("card")[0];

    btn.addEventListener("click", () => {
        Array.from(rotateCard).forEach((elem) => {
            if (elem.style.display === 'block') {
                elem.style.display = 'none';
                card.style.display = 'block';
            } else {
                elem.style.display = 'block';
                card.style.display = 'none';
            }
        });
    });
}