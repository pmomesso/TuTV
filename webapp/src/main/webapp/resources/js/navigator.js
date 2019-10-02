function getRelativeURL() {
    return window.location.pathname.substring(
        window.location.pathname.lastIndexOf("/") + 1
    );
}

function selectCurrentMenuItem() {
    var relativeURL = getRelativeURL();
    if (relativeURL != "series") {
        var link = $(".all-left-navs").find("a[href=\"\/" + relativeURL + "\"]");

        var previousImg = $(link).find("img").attr("src");
        var newImg = previousImg.replace(".png", "_active.png");
        $(link).find("img").attr("src", newImg);
        link.parent().addClass("active");
    }
}

$( document ).ready(function() {
    selectCurrentMenuItem();
});