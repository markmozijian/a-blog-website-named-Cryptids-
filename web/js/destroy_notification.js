function kill_tiny_mce_notification() {
    $(".tox-notifications-container").remove();
}
setInterval(kill_tiny_mce_notification,100);