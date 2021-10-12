function shanchu(id,userid) {
    if (confirm("确认要删除吗？")){
        window.location.href="delete?id=" + id + "&userid=" + userid;
    }
}