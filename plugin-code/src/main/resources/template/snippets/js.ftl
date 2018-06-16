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
            var ctx = document.getElementById('chart-area').getContext("2d");
            new Chart(ctx, eval(${reportDetails.chartJson}));
        </#if>
    })
</script>