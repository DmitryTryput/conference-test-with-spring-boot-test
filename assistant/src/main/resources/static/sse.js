//TODO воткнуть на страничку
function ready() {
    console.log("Ready. Try to listening /answer.sse");
    var source = new EventSource("/answer.sse");
    source.onmessage = function (event) {
        // document.getElementById("main__messages").innerHTML += "<span>fdssf2332d</span>";
        var answer = JSON.parse(event.data);

        var messages = document.getElementById("main__messages");

        var div = document.createElement('div');
        div.innerHTML +=
            "<span class='messages__operatorId'>" + answer.operatorId + "</span>" +
            "<span class='messages__prefix'>" + answer.answerDate + "</span>" +
            "<span class='messages__text'>" + answer.answer + "</span>";

        messages.insertBefore(div, messages.firstChild);

        console.log("=======");
        console.log(event);
        console.log("=======");
    };
}
$(function () {
    $('#sendQuestionForm').submit(function (e) {
        e.preventDefault();
        var questionText = $("#question").val();
        $.ajax({
            type: "POST",
            url: '/question',
            contentType: 'application/json',
            data: JSON.stringify({userId: 'I', body: questionText}),
            success: function (data) {
                console.log(data)
            },
            dataType: 'json'
        });
        console.log("fuck");
        return false;
    });
});

document.addEventListener("DOMContentLoaded", ready);
