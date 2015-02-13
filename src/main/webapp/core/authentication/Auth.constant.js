'use strict';

angular.module('auth')
    .constant('AUTH', {
        userInfo: 'app/rest/user',
        changeRole: 'app/rest/user/changerole'
    });