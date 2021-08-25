angular.module('app').controller('profilesController', function ($scope, $rootScope, $http, $mdDialog) {
    const contextPath = 'http://localhost:8180/app';

    function dialogController($scope, $rootScope, $mdDialog) {
        $scope.departmentnames = $rootScope.departmentnames;
        $scope.unitnames = $rootScope.unitnames;

        $scope.submitCreateNewUser = function () {
            $http.post(contextPath + '/api/v1/profiles/register/', $scope.newUser)
                .then(function successCallback(response) {
                    alert('Добавлен новый сотрудник: ' + $scope.newUser.firstname + ' ' + $scope.newUser.lastname);
                    $scope.newUser = null;
                    $rootScope.getAllProfiles();
                    $mdDialog.cancel();
                }, function errorCallback(response) {
                    $scope.showAlert(response.data.msg);
                });
        };

        $scope.showAlert = function (message) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Предупреждение')
                    .textContent(message)
                    .ok('ОК')
            );
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };
    }

    $scope.showAdvanced = function (ev) {
        $mdDialog.show({
            templateUrl: 'profile/createProfile.html',
            controller: dialogController,
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
        });
    };

    $rootScope.getAllProfiles = function () {
        console.log('getAllProfiles');
        $http({
            url: contextPath + '/api/v1/profiles',
            method: 'GET'
        }).then(function (response) {
            $scope.allProfiles = response.data;
        });
    };

    $scope.getAllDepartments = function () {
        console.log('getAllDepartments');
        $http({
            url: contextPath + '/api/v1/departments/names',
            method: 'GET'
        }).then(function (response) {
            $rootScope.departmentnames = response.data;
            });
    };

    $scope.getAllStaffUnitsNames = function () {
        console.log('getAllStaffUnitsNames');
        $http({
            url: contextPath + '/api/v1/staff_units/names',
            method: 'GET'
        }).then(function (response) {
            $rootScope.unitnames = response.data;
            });
    };

    $scope.getAllDepartments();

    $scope.getAllStaffUnitsNames();

    $scope.getAllProfiles();

});
