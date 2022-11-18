

$(".rsave").on("click", function() {
    console.log("rsave");
    var comment = {
        id: commnet.id,
        content: $('input[name="inputBox"]').val()
    }
    console.log(Comment)
    $.ajax({
        url: '/posts/{postId}/comments',
        method: 'post',
        data: JSON.stringify(comment),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var newComment = parseInt(data);
            alert(newComment + "번 댓글이 등록되었습니다.")
            modal.modal('hide');
            loadJSONData();
        }
    })
})

