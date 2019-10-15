var folderList;
var fileList;
var fileIdList;
var fileKeyList;
var folderIdList;
function getList(select) {
    fileIdList={};
    folderIdList={}
    //获取所有选中的文件ID并转换成列表
    fileIdList=$(":checkbox[name='fileId']:checked").map(function () { return $(this).val() }).get();
    if(select==null) {
        //将文件的id和key选中状态同步
        for (var i = 0; i < $("input[name='fileId']").length; i++) {
            if ($("#fileId" + i).prop("checked")) {
                //遍历选中的文件隐藏
                $("#fileId" + i).closest("tr").attr("hidden", "hidden");
                $("#fileKey" + i).attr("checked", "checked");
            }
        }
        //遍历选中的文件夹隐藏
        for (var i = 0; i < $("input[name='folderId']").length; i++) {
            if ($("#folderId" + i).prop("checked")) {
                $("#folderId" + i).closest("tr").attr("hidden", "hidden");
            }
        }
    }
    fileKeyList=$(":checkbox[name='fileKey']:checked").map(function () { return $(this).val() }).get();
    folderIdList=$(":checkbox[name='folderId']:checked").map(function () { return $(this).val() }).get();
}

// 复选框
function checkall(){
    var checkFileId=document.getElementsByName("fileId");
    var checkFolderId=document.getElementsByName("folderId");
    var cid=document.getElementById("checkAll");		//获取第一行选框的状态
    for(var i=0;i<checkFileId.length;i++){
        checkFileId[i].checked=cid.checked; //将下面行选框的状态和第一个同步
    }
    for(var i=0;i<checkFolderId.length;i++){
        checkFolderId[i].checked=cid.checked; //将下面行选框的状态和第一个同步
    }
}

//执行
function batch(control) {
    getList();
    var json={"control":control,"fileIdList":fileIdList,"fileKeyList":fileKeyList,"folderIdList":folderIdList};
    $.ajax({
        url: pathUrl + "/recycle/controller",
        contentType: 'application/json; charset=UTF-8',
        data:JSON.stringify(json),
        type: "post",
        success: function () {
            console.log("success!");
        }
    });
}