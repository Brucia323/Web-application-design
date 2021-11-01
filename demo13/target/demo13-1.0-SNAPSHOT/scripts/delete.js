function deleteInfo(id) {
    $.get("DeleteServlet", { id }, function () {
        inquire();
    });
}