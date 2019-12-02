function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}

function updateFilteredTable() {
    $.get(context.ajaxUrl + "filter", $("#filter").serialize(), function (data) {
        drawUpdatedTable(data);
    });
}

function updateThisTable() {
    let empty = true;
    $("#filter").find("input").each(function(){
        if($(this).val()) empty = false;
    });
    if(empty){
        updateTable();
    } else {
        updateFilteredTable();
    }
}

$(function () {

    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "<a><span class=\"fa fa-pencil\"></span></a>",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false,
                        render: function (data, type, row) {
                            if (type === "display" && row.id !== undefined) {
                                return "<a onclick=\"deleteRow(" + row.id + ")\"><span class=\"fa fa-remove\"></span></a>";
                            }
                            return data;
                        }
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ],
                "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-mealExcess", data.excess);
                }
            })
        }
    );

    const startDate = $('#startDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
            })
        }
    });

    const endDate = $('#endDate');
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
    });

    const startTime = $('#startTime');
    startTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                maxTime: endTime.val() ? endTime.val() : false
            })
        }
    });

    const endTime = $('#endTime');
    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                minTime: startTime.val() ? startTime.val() : false
            })
        }
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });

});
