'use strict';

angular.module('fmuClientApp')
    .controller('OrderController', ['$scope', '$state', '$filter', 'AuthService','DatetimeService', 'EavropService','gettext',
        function ($scope, $state, $filter, AuthService, DatetimeService, EavropService, gettext) {
            $scope.authService = AuthService;
            $scope.dateKey = 'creationTime';
            $scope.dateTimeService = DatetimeService;

            $scope.click = function (id) {
                $state.go('eavrop.order', {eavropId: id});
            };

            $scope.visa = function () {
                if ($scope.tableParameters) {
                    $scope.tableParameters.reload();
                }
            };

            $scope.bestallningarStatus = EavropService.getEavropConstants.eavropStatus.notAccepted;

            $scope.initTableParameters = function () {
                if (AuthService.hasRole('ROLE_SAMORDNARE')) {
                    $scope.headerFields = [
                        {
                            key: 'arendeId',
                            name: gettext('Beställningar/Ärende-ID')
                        },
                        {
                            key: 'utredningType',
                            name: gettext('Beställningar/Typ')
                        },
                        {
                            key: 'bestallareOrganisation',
                            name: gettext('Beställningar/Organisation')
                        },
                        {
                            key: 'bestallareEnhet',
                            name: gettext('Beställningar/Enhet/Avdelning')
                        },
                        {
                            key: 'creationTime',
                            name: gettext('Beställningar/Förfrågan inkommit datum')
                        },
                        {
                            key: 'patientCity',
                            name: gettext('Beställningar/Den försäkrades bostadsort')
                        },
                        {
                            key: 'mottagarenOrganisation',
                            name: gettext('Beställningar/Organisation')
                        },
                        {
                            key: 'utredareOrganisation',
                            name: gettext('Beställningar/Enhet')
                        },
                        {
                            key: 'status',
                            name: gettext('Beställningar/Status')
                        },
                        {
                            key: 'antalDagarEfterForfragan',
                            name: gettext('Beställningar/Antal dagar efter förfrågan om utredning')
                        }

                    ];

                    $scope.headerGroups = [
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        },
                        {
                            name: gettext('Beställningar/beställare'),
                            colorClass: 'bg-head-danger',
                            colspan: 4
                        },
                        {
                            name: gettext('Beställningar/leverantör'),
                            colorClass: 'bg-head-warning',
                            colspan: 2
                        },
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        }
                    ];
                } else {
                    $scope.headerFields = [
                        {
                            key: 'arendeId',
                            name: gettext('Beställningar/Ärende-ID')
                        },
                        {
                            key: 'utredningType',
                            name: gettext('Beställningar/Typ')
                        },
                        {
                            key: 'bestallareOrganisation',
                            name: gettext('Beställningar/Organisation')
                        },
                        {
                            key: 'bestallareEnhet',
                            name: gettext('Beställningar/Enhet/Avdelning')
                        },
                        {
                            key: 'creationTime',
                            name: gettext('Beställningar/Förfrågan inkommit datum')
                        },
                        {
                            key: 'mottagarenOrganisation',
                            name: gettext('Beställningar/Organisation')
                        },
                        {
                            key: 'utredareOrganisation',
                            name: gettext('Beställningar/Enhet')
                        },
                        {
                            key: 'status',
                            name: gettext('Beställningar/Status')
                        },
                        {
                            key: 'antalDagarEfterForfragan',
                            name: gettext('Beställningar/Antal dagar efter förfrågan om utredning')
                        }

                    ];

                    $scope.headerGroups = [
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        },
                        {
                            name: gettext('Beställningar/beställare'),
                            colorClass: 'bg-head-danger',
                            colspan: 3
                        },
                        {
                            name: gettext('Beställningar/leverantör'),
                            colorClass: 'bg-head-warning',
                            colspan: 2
                        },
                        {
                            name: null,
                            colorClass: null,
                            colspan: 2
                        }
                    ];
                }
            };

            $scope.footerHints = [
                {
                    description: gettext('Beställningar/Antal dagar har överträtts och/eller annan avvikelse finns'),
                    colorClass: 'bg-danger'
                }
            ];

            $scope.datePickerDescription = gettext('Beställningar/Datumen utgår från det datum då beställningen inkommit');

            $scope.getTableCellValue = function (key, rowData) {
                switch (key) {
                    case 'creationTime':
                        return $filter('date')(rowData[key], EavropService.getEavropConstants.dateFormat);
                    case 'status':
                        return EavropService.getEavropConstants.statusMapping[rowData[key]];
                    default:
                        return rowData[key] ? rowData[key] : '&nbsp;';
                }
            };

            $scope.$watch('startDate', function(){

            });

            // TODO fix this with event ?
            $scope.$watch('authService', function () {
                $scope.initTableParameters();
            }, true);
        }
    ]);
