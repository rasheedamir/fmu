'use strict';

angular.module('fmuClientApp')
    .controller('CompensationController', ['$scope', '$filter', 'AuthService', 'currentEavrop',
        function ($scope, $filter, AuthService, currentEavrop) {
            $scope.authService = AuthService;
            $scope.currentEavrop = currentEavrop;
            function initFields() {
                currentEavrop.$promise.then(function () {
                    $scope.arendeHeaderFields = [
                        {
                            key: 'arendeId',
                            name: 'Ärende-ID'
                        },
                        {
                            key: 'utredningType',
                            name: 'Typ'
                        },
                        {
                            key: 'vardenhet',
                            name: 'Utförare, organisation'
                        },
                        {
                            key: 'utredareName',
                            name: 'Utförare, namn'
                        },
                        {
                            key: 'utredningType',
                            name: 'Tolk anlitad?'
                        },
                        {
                            key: 'bestallareOrganisation',
                            name: 'Utredningen genomfördes på, antal dagar'
                        },
                        {
                            key: 'bestallareEnhet',
                            name: 'Antal dagar efter komplettering'
                        },
                        {
                            key: 'bestallareEnhet',
                            name: 'Antal avikelser'
                        },
                        {
                            key: 'bestallareEnhet',
                            name: 'Antal utredningsstarter'
                        },
                        {
                            key: 'utredareOrganisation',
                            name: 'Utredning är komplett och godkänd?'
                        }

                    ];

                    $scope.specificationHeaderFields = [
                        {
                            key: 'arendeId',
                            name: 'Avvikelse'
                        }

                    ];

                    if(currentEavrop.utredningType == 'AFU'){
                        $scope.tillaggtjanstHeaderFields = [
                            {
                                key: 'arendeId',
                                name: 'Tillägstjänst'
                            },
                            {
                                key: 'utredningType',
                                name: 'Timmar'
                            },
                            {
                                key: 'utredningType',
                                name: 'Tolk'
                            }
                        ];
                    }
                });
            }

            initFields();
        }
    ])
;
