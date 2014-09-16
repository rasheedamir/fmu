'use strict';

describe('Controllers Tests ', function () {

    beforeEach(module('fmuApp'));

    describe('LoginController', function () {
        var $scope;


        beforeEach(inject(function ($rootScope, $controller) {
            $scope = $rootScope.$new();
            $controller('LoginController', {$scope: $scope});
        }));

        it('should set remember Me', function () {
            expect($scope.rememberMe).toBeTruthy();
        });
    });

    describe('PasswordController', function(){
       var $scope,
           PasswordService;

        beforeEach(inject(function($rootScope, $controller, Password) {
            $scope = $rootScope.$new();
            PasswordService = Password;
            $controller('PasswordController',{$scope:$scope, Password:PasswordService});
        }));

        it('should show error if passwords do not match', function(){
            //GIVEN
            $scope.password = 'password1';
            $scope.confirmPassword = 'password2';
            //WHEN
            $scope.changePassword();
            //THEN
            expect($scope.doNotMatch).toBe('ERROR');

        });
        it('should call Service and set OK on Success', function(){
            //GIVEN
            var pass = 'myPassword';
            $scope.password = pass;
            $scope.confirmPassword = pass;
            //SET SPY
            spyOn(PasswordService, 'save');

            //WHEN
            $scope.changePassword();

            //THEN
            expect(PasswordService.save).toHaveBeenCalled();
            expect(PasswordService.save).toHaveBeenCalledWith(pass, jasmine.any(Function), jasmine.any(Function));
            //SIMULATE SUCCESS CALLBACK CALL FROM SERVICE
            PasswordService.save.calls.mostRecent().args[1]();
            expect($scope.error).toBeNull();
            expect($scope.success).toBe('OK');
        });
    });

    describe('SettingsController', function () {
        var $scope, AccountService;

        beforeEach(inject(function ($rootScope, $controller, Account) {
            $scope = $rootScope.$new();

            AccountService = Account;
            $controller('SettingsController',{$scope:$scope, resolvedAccount:AccountService, Account:AccountService});
        }));

        it('should save account', function () {
            //GIVEN
            $scope.settingsAccount = {firstName: "John", lastName: "Doe"};

            //SET SPY
            spyOn(AccountService, 'save');

            //WHEN
            $scope.save();

            //THEN
            expect(AccountService.save).toHaveBeenCalled();
                        expect(AccountService.save).toHaveBeenCalledWith({firstName: "John", lastName: "Doe"}, jasmine.any(Function), jasmine.any(Function));

            //SIMULATE SUCCESS CALLBACK CALL FROM SERVICE
            AccountService.save.calls.mostRecent().args[1]();
            expect($scope.error).toBeNull();
            expect($scope.success).toBe('OK');
        });
    });

    describe('SessionsController', function () {
        var $scope, SessionsService;

        beforeEach(inject(function ($rootScope, $controller, Sessions) {
            $scope = $rootScope.$new();

            SessionsService = Sessions;
            $controller('SessionsController',{$scope:$scope, resolvedSessions:SessionsService, Sessions:SessionsService});
        }));

        it('should invalidate session', function () {
            //GIVEN
            $scope.series = "123456789";

            //SET SPY
            spyOn(SessionsService, 'delete');

            //WHEN
            $scope.invalidate($scope.series);

            //THEN
            expect(SessionsService.delete).toHaveBeenCalled();
            expect(SessionsService.delete).toHaveBeenCalledWith({series: "123456789"}, jasmine.any(Function), jasmine.any(Function));

            //SIMULATE SUCCESS CALLBACK CALL FROM SERVICE
            SessionsService.delete.calls.mostRecent().args[1]();
            expect($scope.error).toBeNull();
            expect($scope.success).toBe('OK');
        });
    });

    describe("EavropController", function(){
        var rootScope, scope, controller, serviceMock, filter, ngTableParams, EAVROPHEADERS, DateSelectionChangeService, data;

        beforeEach(inject(function(
            $rootScope, $controller, $q, _$filter_, _ngTableParams_, _EAVROPHEADERS_, _DateSelectionChangeService_, $httpBackend) {
            rootScope = $rootScope;
            scope = $rootScope.$new();
            controller = $controller;
            filter = _$filter_;
            ngTableParams = _ngTableParams_;
            EAVROPHEADERS = _EAVROPHEADERS_;
            DateSelectionChangeService =_DateSelectionChangeService_;

            data = [{"arendeId":"123421","utredningType":"AFU","bestallareOrganisation":"In progress","enhet":"In progress","creationTime":1445451264483,"patientCity":"LinkÃ¶ping","mottagarenOrganisation":"In progress","utredare":"In progress","status":"In progress","antalDagarEfterForfragan":93,"color":"#105cc7"},
                {"arendeId":"753423","utredningType":"SLU","bestallareOrganisation":"In progress","enhet":"In progress","creationTime":1490811264484,"patientCity":"GÃ¶teborg","mottagarenOrganisation":"In progress","utredare":"In progress","status":"In progress","antalDagarEfterForfragan":89,"color":"#10e722"},
                {"arendeId":"44240","utredningType":"AFU","bestallareOrganisation":"In progress","enhet":"In progress","creationTime":1481310864484,"patientCity":"Stockholm","mottagarenOrganisation":"In progress","utredare":"In progress","status":"In progress","antalDagarEfterForfragan":92,"color":"#105d5c"},
                {"arendeId":"78743","utredningType":"TMU","bestallareOrganisation":"In progress","enhet":"In progress","creationTime":1492884864484,"patientCity":"oskarshamn","mottagarenOrganisation":"In progress","utredare":"In progress","status":"In progress","antalDagarEfterForfragan":95,"color":"#107934"}];

            serviceMock = {
                getEavrops: function(){
                    // mock promise
                    var deferred = $q.defer();
                    deferred.resolve(data);
                    return deferred.promise;
                }
            }

            $httpBackend.when('GET', 'i18n/en.json').respond({});
            $httpBackend.flush();
        }));

        it('should call EavropService and set result in the scope', function(){
            controller("EavropController",
                {
                    $scope: scope,
                    $filter: filter,
                    EavropService: serviceMock,
                    ngTableParams: ngTableParams,
                    EAVROPHEADERS: EAVROPHEADERS,
                    DateSelectionChangeService: DateSelectionChangeService
                });

            //causes promises to check to see if they are fulfilled
            scope.$apply();
            expect(scope.eavrops).toEqual(data);
        });


    });
});
