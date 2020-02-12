import $ from 'jquery';

function extend() {
    if ($(".page-left").hasClass("extended")) {
        $(".page-left").removeClass("extended");
    } else {
        $(".page-left").addClass("extended");
    }
}

function confirmAction(event, message) {
    event.preventDefault();

    var form = event.target;
    var accept = window.confirm(message);

    if (accept)
        form.submit();
}

$( document ).ready(function() {

    var sectionId = $('#sectionId').val();
    if (typeof sectionId != "undefined") {
        window.location.hash = "#" + sectionId;
    }
});


function functionConfirm(msg, myYes, myNo) {
    var confirmBox = $("#confirm");
    confirmBox.find(".message").text(msg);
    confirmBox.find(".yes,.no").unbind().click(function() {
        confirmBox.hide();
    });
    confirmBox.find(".yes").click(myYes);
    confirmBox.find(".no").click(myNo);
    confirmBox.show();
}
