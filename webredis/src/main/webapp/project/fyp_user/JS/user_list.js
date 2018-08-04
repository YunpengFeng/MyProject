
$( function(){
    $.ajax({
        type:'POST',
        url:'../../UserCRUD/synRequest',
        dataType:'json',
        success:function(data){
            var html ="";
            for(var i in data){
                html += '<div style="color:red">姓名：'+data[i].userName+';年齡：'+data[i].age+';性别：'+data[i].sex+"<br></div>";
            }
            $("#test").html(html);
        }

    })
})