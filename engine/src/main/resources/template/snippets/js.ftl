<#--
Copyright 2023 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/datatables.min.js"></script>
<script src="js/jquery.fancybox.min.js"></script>
<script src="js/Chart.bundle.min.js"></script>

<script>
    $(document).ready(function () {
            // Data tables
            $('.renderAsDataTable').on('draw.dt', function () {
                $('[data-toggle="tooltip"]').tooltip();
            }).DataTable({
                "oLanguage": {
                    "sSearch": "Search:"
                },
                "pageLength": 25,
                "responsive": true
            });

            $('.collapse').on('shown.bs.collapse', function (e) {
                $(e.target).find("iframe").each(function (index, iframe) {
                    resizeIframe(iframe);
                })
            });

            // Lightbox
            $("a.grouped_elements").fancybox();

            // Tool tips
            $('[data-toggle="tooltip"]').tooltip();

            // Chart
            <#if (reportDetails.chartJson?has_content)>
                var canvas = document.getElementById('chart-area');
                const ctx = canvas.getContext("2d");
                const chart = new Chart(ctx, ${reportDetails.chartJson});
                let original;
                if (chart.config.type === "pie") {
                    original = Chart.defaults.pie.legend.onClick;
                    chart.options.onClick = function (evt, elements) {
                        chartArea = elements[0];
                        if (chartArea === undefined) return;
                        chartArea.hidden = !chartArea.hidden;
                        chart.update();
                        toggleVisibilityByStatus(chartArea._model.label, !chartArea.hidden)
                    };
                } else if (chart.config.type === "bar") {
                    <#if (reportDetails.chartUrlLookup?has_content)>
                        const chartUrls = {
                            <#list reportDetails.chartUrlLookup as stepName, urlFriendlyStepName>
                            "${stepName?js_string}": "${urlFriendlyStepName}",
                            </#list>
                        };
                        canvas.onclick = function (evt) {
                            const activePoints = chart.getElementsAtEvent(evt);
                            if (activePoints.length <= 0) return;
                            const clickedElementindex = activePoints[0]["_index"];
                            const label = chart.data.labels[clickedElementindex];
                            if (label == null) return;
                            urlSnippet = chartUrls[label];
                            if (urlSnippet == null) return;
                            window.location.href = urlSnippet;
                        }
                    </#if>
                    original = Chart.defaults.global.legend.onClick;
                }

                chart.options.legend.onClick = function (evt, label) {
                    original.call(this, evt, label);
                    toggleVisibilityByStatus(label.text, label.hidden);
                };

                function toggleVisibilityByStatus(statusText, show) {
                    const card = $("#card_" + statusText);
                    if (card !== undefined) {
                        if (show) {
                            card.show();
                        } else {
                            card.hide();
                        }
                    }

                    const row = $(".table-row-" + statusText);
                    if (row !== undefined) {
                        if (show) {
                            row.show();
                        } else {
                            row.hide();
                        }
                    }
                }
            </#if>

            if (${expandBeforeAfterHooks?c}) {
                $(".btn-outline-secondary[data-cluecumber-item='before-after-hooks-button']").click();
            }
            if (${expandStepHooks?c}) {
                $(".btn-outline-secondary[data-cluecumber-item='step-hooks-button']").click();
            }
            if (${expandDocStrings?c}) {
                $(".btn-outline-secondary[data-cluecumber-item='doc-strings-button']").click();
            }
            if (${expandPreviousRuns?c}) {
                $(".btn-outline-secondary[data-cluecumber-item='show-not-last-runs-button']").click();
            }
        }
    );

    function resizeIframe(iframe) {
        iframe.style.height = (iframe.contentWindow.document.body.scrollHeight + 25) + 'px';
    }
</script>