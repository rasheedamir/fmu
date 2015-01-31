'use strict';

angular.module('fmuClientApp')
    .controller('OngoingController', ['$scope', '$filter', 'AuthService', 'DatetimeService', 'EavropService','gettext',
        function ($scope, $filter, AuthService, DatetimeService, EavropService, gettext) {
            $scope.authService = AuthService;
            $scope.datetimeService = DatetimeService;
            $scope.dateKey = 'startDate';

            $scope.ongoingStatus = EavropService.getEavropConstants.eavropStatus.accepted;

            $scope.headerFields = [
                {
                    key: 'arendeId',
                    name: gettext('Pågående-utredningar/Ärende-ID')
                },
                {
                    key: 'utredningType',
                    name: gettext('Pågående-utredningar/Typ')
                },
                {
                    key: 'bestallareOrganisation',
                    name: gettext('Pågående-utredningar/Organisation')
                },
                {
                    key: 'bestallareEnhet',
                    name: gettext('Pågående-utredningar/Enhet/Avdelning')
                },
                {
                    key: 'utredareOrganisation',
                    name: gettext('Pågående-utredningar/Organisation')
                },
                {
                    key: 'status',
                    name: gettext('Pågående-utredningar/Status')
                },
                {
                    key: 'startDate',
                    name: gettext('Pågående-utredningar/Utredning start')
                },
                {
                    key: 'nrOfDaysSinceStart',
                    name: gettext('Pågående-utredningar/Antal dagar från start')
                },
                {
                    key: 'avikelser',
                    name: gettext('Pågående-utredningar/Avikelser')
                }
            ];

            $scope.headerGroups = [
                {
                    name: null,
                    colorClass: null,
                    colspan: 2
                },
                {
                    name: gettext('Pågående-utredningar/beställare'),
                    colorClass: 'bg-head-danger',
                    colspan: 2
                },
                {
                    name: gettext('Pågående-utredningar/leverantör'),
                    colorClass: 'bg-head-warning',
                    colspan: 1
                },
                {
                    name: null,
                    colorClass: null,
                    colspan: 4
                }
            ];

            $scope.footerHints = [
                {
                    description: gettext('Pågående-utredningar/Antal dagar har överträtts och/eller annan avvikelse finns'),
                    colorClass: 'bg-danger'
                }
            ];

            $scope.datePickerDescription = gettext('Pågående-utredningar/Datumen utgår från det datum då utredningen startat');

            $scope.visa = function () {
                if($scope.tableParameters){
                    $scope.tableParameters.reload();
                }
            };

            $scope.getTableCellValue = function (key, rowData) {
                switch (key) {
                    case 'startDate':
                        return $filter('date')(rowData[key], EavropService.getEavropConstants.dateFormat);
                    case 'status':
                        return EavropService.getEavropConstants.statusMapping[rowData[key]];
                    default:
                        return rowData[key];
                }
            };
            
        }
    ]);
