<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/datatables.min.js"></script>
<script src="js/jquery.fancybox.min.js"></script>
<script src="js/Chart.bundle.min.js"></script>

<script>
    $(document).ready(function () {
        // Data tables
        var dataTable = $('.renderAsDataTable').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip();
        }).DataTable({
            "oLanguage": {
                "sSearch": "Search all columns:"
            }
        });

        // Lightbox
        $("a.grouped_elements").fancybox();

        // Tool tips
        $('[data-toggle="tooltip"]').tooltip();

        // Chart
        <#if (reportDetails.chartJson?has_content)>
            var canvas = document.getElementById('chart-area');
            var ctx = canvas.getContext("2d");
            var chart = new Chart(ctx, eval(${reportDetails.chartJson}));

            var original;
            switch (chart.config.type) {
                case "pie":
                    original = Chart.defaults.pie.legend.onClick;
                    break;
                default:
                    original = Chart.defaults.global.legend.onClick;
                    break;
            }

            chart.options.legend.onClick = function (evt, label) {
                original.call(this, evt, label);

                var card = $("#card_" + label.text);
                label.hidden ? card.show() : card.hide();

                var row = $(".row_" + label.text);
                label.hidden ? row.show() : row.hide();
            };
        </#if>
    })
</script>