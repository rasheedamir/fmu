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
/*
    describe('EavropController', function(){
        var scope, eavropService;

        beforeEach(function(){
            var mockRestService = {};

            module('fmuApp', function($provide){
                $provide.value('EavropService', mockRestService);
            });

            inject(function($q){
                mockRestService.data = [
                    {"id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                    {"id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                    {"id":"11221213", "typ":"TMU", "datum":"2014/10/12"}
                ];

                mockRestService.getEavrops = function(){
                    var defer = $q.defer();
                    defer.resolve(this.data);
                    return defer.promise;
                };
            });

            beforeEach(inject(function($controller, $rootScope, _EavropService_, _$filter_, _ngTableParams_,_EAVROPHEADERS_){
                scope = $rootScope.new();
                eavropService = _EavropService_;
                $controller('EavropController', {'$scope': $rootScope,'$filter': _$filter_, 'EavropService':_EavropService_, 'ngTableParams': _ngTableParams_,'EAVROPHEADERS': _EAVROPHEADERS_});
                scope.$digest();
            }));

            it("should fail", function(){
                console.log("Running");
                expect(0).toEqual(0);
            });
        });
    });
    */
});
