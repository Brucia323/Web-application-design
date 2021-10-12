function shanchu(id,userid) {
    if (confirm("确认要删除吗？")){
        window.location.href="DeleteServlet?id=" + id + "&userid=" + userid;
    }
}