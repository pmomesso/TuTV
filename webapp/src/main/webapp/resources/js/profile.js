$(document).ready(function() {
    $("#showUploadAvatarPopup").on('click', function () {
        $("#uploadAvatarPopup").toggle();
    });
    $('#avatarFileInput').on('change',function() {
        var fileInput = $('#avatarFileInput');
        var maxSize = fileInput.data('max-size');
        if (fileInput.get(0).files.length) {
            var fileSize = fileInput.get(0).files[0].size; // in bytes
            if (fileSize > maxSize) {
                $("#avatarMaxSizeError").show();
                return false;
            }
            else{
                $("#avatarMaxSizeError").hide();
                $("#avatarFileForm").submit();
            }
        } else {
            return false;
        }
    });
    $('#followedLink').on('click',function() {
        $('#followedTab').addClass('active');
        $('#tab-shows').addClass('active');
        $('#informationTab').removeClass('active');
        $('#tab-information').removeClass('active');
    });
    $('#informationLink').on('click',function() {
        $('#informationTab').addClass('active');
        $('#tab-information').addClass('active');
        $('#followedTab').removeClass('active');
        $('#tab-shows').removeClass('active');
    });
});