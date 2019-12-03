function enable(input, id) {
    let e = input.is(":checked");
    $.ajax({
        url: context.ajaxUrl + "enable",
        type: "POST",
        data: {enable: e, id: id}
    }).done( function (data) {
            if (data) {
                input.closest("tr").attr("data-userEnabled", e);
                if (e)
                    successNoty("Enabled");
                else
                    successNoty("Disabled");
            }
        }
    ).fail(function () {
        input.prop("checked", !e);
    });
}

function updateThisTable() {
    updateTable();
}

$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled",
                        render: function (data, type, row) {
                            if (type === "display" && row.id !== undefined) {
                                return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='enable($(this)," + row.id + ")'/>";
                            }
                            return data;
                        }
                    },
                    {
                        "data": "registered",
                        render: function (data, type, row) {
                            if (type === "display" && row.id !== undefined) {
                                return data.substring(0, 10);
                            }
                            return data;
                        }
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
                                return "<a class=\"delete\" onclick=\"deleteRow(" + row.id + ")\"><span class=\"fa fa-remove\"></span></a>";
                            }
                            return data;
                        }
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ],
                "createdRow": function (row, data, dataIndex) {
                    if (!data.enabled) {
                        $(row).attr("data-userEnabled", false);
                    }
                },
                "updateTable": function () {
                    $.get("ajax/admin/users/", updateTable);
                }
            })
        }
    );
});