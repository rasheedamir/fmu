'use strict';

/* Controllers */

fmuApp.controller('MainController', ['$scope',
    function ($scope) {
    }]);

fmuApp.controller('AdminController', ['$scope',
    function ($scope) {
    }]);

fmuApp.controller('LanguageController', ['$scope', '$translate', 'LanguageService', 'FLAGS',
    function ($scope, $translate, LanguageService, FLAGS) {
        $scope.getFlagClass = function(language) {
            var selectedFlag = "";
            angular.forEach(FLAGS, function(flag, flagLanguage){
                if (language == flagLanguage) {
                    selectedFlag = flag;
                }
            });
            return "famfamfam-flag-" + selectedFlag;
        };

        $scope.changeLanguage = function (languageKey) {
            $translate.use(languageKey);

            LanguageService.getBy(languageKey).then(function(languages) {
                $scope.languages = languages;
            });
        };

        LanguageService.getBy().then(function (languages) {
            $scope.languages = languages;
        });
    }]);

fmuApp.controller('MenuController', ['$scope',
    function ($scope) {
    }]);

fmuApp.controller('LoginController', ['$scope', '$location', 'AuthenticationSharedService',
    function ($scope, $location, AuthenticationSharedService) {
        $scope.rememberMe = true;
        $scope.login = function () {
            AuthenticationSharedService.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            })
        }
    }]);

fmuApp.controller('LogoutController', ['$location', 'AuthenticationSharedService',
    function ($location, AuthenticationSharedService) {
        AuthenticationSharedService.logout();
    }]);

fmuApp.controller('SettingsController', ['$scope', 'Account',
    function ($scope, Account) {
        $scope.success = null;
        $scope.error = null;
        $scope.settingsAccount = Account.get();

        $scope.save = function () {
            Account.save($scope.settingsAccount,
                function (value, responseHeaders) {
                    $scope.error = null;
                    $scope.success = 'OK';
                    $scope.settingsAccount = Account.get();
                },
                function (httpResponse) {
                    $scope.success = null;
                    $scope.error = "ERROR";
                });
        };
    }]);

fmuApp.controller('RegisterController', ['$scope', '$translate', 'Register',
    function ($scope, $translate, Register) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.register = function () {
            if ($scope.registerAccount.password != $scope.confirmPassword) {
                $scope.doNotMatch = "ERROR";
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                Register.save($scope.registerAccount,
                    function (value, responseHeaders) {
                        $scope.error = null;
                        $scope.errorUserExists = null;
                        $scope.success = 'OK';
                    },
                    function (httpResponse) {
                        $scope.success = null;
                        if (httpResponse.status === 304 &&
                                httpResponse.data.error && httpResponse.data.error === "Not Modified") {
                            $scope.error = null;
                            $scope.errorUserExists = "ERROR";
                        } else {
                            $scope.error = "ERROR";
                            $scope.errorUserExists = null;
                        }
                    });
            }
        }
    }]);

fmuApp.controller('ActivationController', ['$scope', '$routeParams', 'Activate',
    function ($scope, $routeParams, Activate) {
        Activate.get({key: $routeParams.key},
            function (value, responseHeaders) {
                $scope.error = null;
                $scope.success = 'OK';
            },
            function (httpResponse) {
                $scope.success = null;
                $scope.error = "ERROR";
            });
    }]);

fmuApp.controller('PasswordController', ['$scope', 'Password',
    function ($scope, Password) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.changePassword = function () {
            if ($scope.password != $scope.confirmPassword) {
                $scope.doNotMatch = "ERROR";
            } else {
                $scope.doNotMatch = null;
                Password.save($scope.password,
                    function (value, responseHeaders) {
                        $scope.error = null;
                        $scope.success = 'OK';
                    },
                    function (httpResponse) {
                        $scope.success = null;
                        $scope.error = "ERROR";
                    });
            }
        };
    }]);

fmuApp.controller('SessionsController', ['$scope', 'resolvedSessions', 'Sessions',
    function ($scope, resolvedSessions, Sessions) {
        $scope.success = null;
        $scope.error = null;
        $scope.sessions = resolvedSessions;
        $scope.invalidate = function (series) {
            Sessions.delete({series: encodeURIComponent(series)},
                function (value, responseHeaders) {
                    $scope.error = null;
                    $scope.success = "OK";
                    $scope.sessions = Sessions.get();
                },
                function (httpResponse) {
                    $scope.success = null;
                    $scope.error = "ERROR";
                });
        };
    }]);

 fmuApp.controller('MetricsController', ['$scope', 'MetricsService', 'HealthCheckService', 'ThreadDumpService',
    function ($scope, MetricsService, HealthCheckService, ThreadDumpService) {

        $scope.refresh = function() {
            HealthCheckService.check().then(function(data) {
                $scope.healthCheck = data;
            });

            $scope.metrics = MetricsService.get();

            $scope.metrics.$get({}, function(items) {

                $scope.servicesStats = {};
                $scope.cachesStats = {};
                angular.forEach(items.timers, function(value, key) {
                    if (key.indexOf("web.rest") != -1 || key.indexOf("service") != -1) {
                        $scope.servicesStats[key] = value;
                    }

                    if (key.indexOf("net.sf.ehcache.Cache") != -1) {
                        // remove gets or puts
                        var index = key.lastIndexOf(".");
                        var newKey = key.substr(0, index);

                        // Keep the name of the domain
                        index = newKey.lastIndexOf(".");
                        $scope.cachesStats[newKey] = {
                            'name': newKey.substr(index + 1),
                            'value': value
                        };
                    }
                });
            });
        };

        $scope.refresh();

        $scope.threadDump = function() {
            ThreadDumpService.dump().then(function(data) {
                $scope.threadDump = data;

                $scope.threadDumpRunnable = 0;
                $scope.threadDumpWaiting = 0;
                $scope.threadDumpTimedWaiting = 0;
                $scope.threadDumpBlocked = 0;

                angular.forEach(data, function(value, key) {
                    if (value.threadState == 'RUNNABLE') {
                        $scope.threadDumpRunnable += 1;
                    } else if (value.threadState == 'WAITING') {
                        $scope.threadDumpWaiting += 1;
                    } else if (value.threadState == 'TIMED_WAITING') {
                        $scope.threadDumpTimedWaiting += 1;
                    } else if (value.threadState == 'BLOCKED') {
                        $scope.threadDumpBlocked += 1;
                    }
                });

                $scope.threadDumpAll = $scope.threadDumpRunnable + $scope.threadDumpWaiting +
                    $scope.threadDumpTimedWaiting + $scope.threadDumpBlocked;

            });
        };

        $scope.getLabelClass = function(threadState) {
            if (threadState == 'RUNNABLE') {
                return "label-success";
            } else if (threadState == 'WAITING') {
                return "label-info";
            } else if (threadState == 'TIMED_WAITING') {
                return "label-warning";
            } else if (threadState == 'BLOCKED') {
                return "label-danger";
            }
        };
    }]);

fmuApp.controller('LogsController', ['$scope', 'resolvedLogs', 'LogsService',
    function ($scope, resolvedLogs, LogsService) {
        $scope.loggers = resolvedLogs;

        $scope.changeLevel = function (name, level) {
            LogsService.changeLevel({name: name, level: level}, function () {
                $scope.loggers = LogsService.findAll();
            });
        }
    }]);

fmuApp.controller('AuditsController', ['$scope', '$translate', '$filter', 'AuditsService',
    function ($scope, $translate, $filter, AuditsService) {
        $scope.onChangeDate = function() {
            AuditsService.findByDates($scope.fromDate, $scope.toDate).then(function(data){
                $scope.audits = data;
            });
        };

        // Date picker configuration
        $scope.today = function() {
            // Today + 1 day - needed if the current day must be included
            var today = new Date();
            var tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate()+1); // create new increased date

            $scope.toDate = $filter('date')(tomorrow, "yyyy-MM-dd");
        };

        $scope.previousMonth = function() {
            var fromDate = new Date();
            if (fromDate.getMonth() == 0) {
                fromDate = new Date(fromDate.getFullYear() - 1, 0, fromDate.getDate());
            } else {
                fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
            }

            $scope.fromDate = $filter('date')(fromDate, "yyyy-MM-dd");
        };

        $scope.today();
        $scope.previousMonth();
        
        AuditsService.findByDates($scope.fromDate, $scope.toDate).then(function(data){
            $scope.audits = data;
        });
    }]);

fmuApp.controller('EavropController', ['$scope','$filter', 'EavropService', 'ngTableParams','EAVROPHEADERS','DateSelectionChangeService',
    function($scope, $filter, EavropService, ngTableParams, EAVROPHEADERS ,DateSelectionChangeService){
        $scope.tableHeaders = EAVROPHEADERS;
        $scope.tableKeys = [];
        var dateSortKey = 'creationTime';
        var eavrops = [];

        var applyDateFilter = function(eavrops){
            return $filter('dateFilter')(eavrops, dateSortKey, DateSelectionChangeService.startDate, DateSelectionChangeService.endDate);
        };


        // Fetch eavrops data from server
        EavropService.getEavrops().then(function(result){
            eavrops = result;
            if(eavrops != null && result.length > 0){
                // Set initial date range
                if(eavrops.length > 0){
                    $scope.tableKeys = _.keys(result[0]);
                    var ordered = $filter('orderBy')(eavrops, dateSortKey, false);
                    DateSelectionChangeService.setInitialDateRange(_.first(ordered)[dateSortKey], _.last(ordered)[dateSortKey]);
                }
            }

            // Set up ngtable options
            var filteredData = applyDateFilter(eavrops);
            if(!$scope.tableParams){
                $scope.tableParams = new ngTableParams({
                    page: 1,            // show first page
                    count: 10          // count per page
                }, {
                    total: filteredData.length, // length of data
                    getData: function($defer, params) {
                        filteredData = params.sorting() ?
                            $filter('orderBy')(filteredData, params.orderBy()) :
                            filteredData;

                        $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                    }
                });
                $scope.tableParams.settings().$scope = $scope;

            }

            $scope.getFormattedDate = function(date){
                return $filter('date')(date, 'dd-MM-yyyy');
            }

            $scope.sort = function(key, tableParams){
                var params = {};
                params[key] = tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                tableParams.sorting(params);
                $scope.$broadcast();
            }

            // Listen to date changes and apply filter
            $scope.$on('newDateSelected', function(){
                filteredData = applyDateFilter(eavrops);
                $scope.tableParams.reload();
            });
        });
    }]);

fmuApp.controller('DateSelectionController', ['$scope', 'DateSelectionChangeService',
    function ($scope, DateSelectionChangeService) {

        $scope.$on('initialDateIsSet', function(){
            $scope.startDate = new Date(DateSelectionChangeService.startDate);
            $scope.endDate = new Date(DateSelectionChangeService.endDate);
        });

        $scope.doFilter = function(){
            DateSelectionChangeService.update($scope.startDate, $scope.endDate);
        };

        $scope.clearStartDate = function () {
            $scope.startDate = null;
        };

        $scope.clearEndDate = function () {
            $scope.endDate = null;
        };

        // Disable weekend selection
        $scope.disabled = function(date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.openStart = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.startDateOpened = true;
        };

        $scope.openEnd = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.endDateOpened = true;
        };
        $scope.dateFormat = 'dd-MM-yyyy';
    }]);