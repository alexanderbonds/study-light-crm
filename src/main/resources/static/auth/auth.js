angular.module('app').controller('authController', function ($scope, $http, $location, $localStorage, $rootScope, jwtHelper, chatService) {
    const contextPath = 'http://localhost:8180/app';

    $scope.date = new Date();

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer '
                        + response.data.token;

                    $scope.tokenPayload = jwtHelper.decodeToken(response.data.token);
                    $localStorage.currentUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        roles: $scope.tokenPayload.roles,
                        profileId: $scope.tokenPayload.profileId
                    };

                    $scope.user.username = null;
                    $scope.user.password = null;

                    //Подключение к вебсокету
                    chatService.ready = chatService.connect('/app/ws', {}, function (error) {
                        alert(error);
                    });

                    $location.path('/');
                    console.log($localStorage.currentUser);
                }
            }, function errorCallback(response) {
                console.log(response.data);
                window.alert(response.data.msg);
                $scope.clearUser();
            });
    };
});