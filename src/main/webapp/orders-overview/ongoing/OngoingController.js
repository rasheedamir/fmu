(function() {
    'use strict';

    angular.module('fmu.overview')
        .controller('OngoingController', OngoingController);
    OngoingController.$inject = ['$scope', '$filter', 'AuthService', 'DatetimeService', 'eavropService', 'gettext'];

    function OngoingController($scope, $filter, AuthService, DatetimeService, eavropService, gettext) {
        $scope.authService = AuthService;
        $scope.datetimeService = DatetimeService;
        $scope.dateKey = 'startDate';

        $scope.ongoingStatus = eavropService.getEavropConstants.eavropStatus.accepted;

        $scope.headerFields = [{
            key: 'arendeId',
            name: gettext('Pågående-utredningar/Ärende-ID')
        }, {
            key: 'utredningType',
            name: gettext('Pågående-utredningar/Typ')
        }, {
            key: 'bestallareOrganisation',
            name: gettext('Pågående-utredningar/Organisation')
        }, {
            key: 'bestallareEnhet',
            name: gettext('Pågående-utredningar/Enhet/Avdelning')
        }, {
            key: 'utredareOrganisation',
            name: gettext('Pågående-utredningar/Organisation')
        }, {
            key: 'status',
            name: gettext('Pågående-utredningar/Status')
        }, {
            key: 'startDate',
            name: gettext('Pågående-utredningar/Utredning start')
        }, {
            key: 'nrOfDaysSinceStart',
            name: gettext('Pågående-utredningar/Antal dagar från start')
        }, {
            key: 'avikelser',
            name: gettext('Pågående-utredningar/Avikelser')
        }];

        $scope.headerGroups = [{
            name: null,
            colorClass: null,
            colspan: 2
        }, {
            name: gettext('Pågående-utredningar/beställare'),
            colorClass: 'bg-head-danger',
            colspan: 2
        }, {
            name: gettext('Pågående-utredningar/leverantör'),
            colorClass: 'bg-head-warning',
            colspan: 1
        }, {
            name: null,
            colorClass: null,
            colspan: 4
        }];

        $scope.footerHints = [{
            description: gettext('Pågående-utredningar/Antal dagar har överträtts och/eller annan avvikelse finns'),
            colorClass: 'bg-danger'
        }];

        $scope.datePickerDescription = gettext('Pågående-utredningar/Datumen utgår från det datum då utredningen startat');

        $scope.visa = visaFn;
        $scope.getTableCellValue = getTableCellValueFn;


        function visaFn() {
            if ($scope.tableParameters) {
                $scope.tableParameters.reload();
            }
        }

        function getTableCellValueFn(key, rowData) {
            switch (key) {
                case 'startDate':
                    return $filter('date')(rowData[key], eavropService.getEavropConstants.dateFormat);
                case 'status':
                    return eavropService.getEavropConstants.statusMapping[rowData[key]];
                default:
                    return rowData[key];
            }
        }

    }
})();