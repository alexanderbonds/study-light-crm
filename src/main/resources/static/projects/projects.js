angular.module('app').controller('projectController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8180/app';


    $scope.getProjects = function () {
        $http({
            url: contextPath + '/api/v1/projects',
            method: 'GET',
            params: {
                managerId : $scope.filter ? $scope.filter.managerId : null,
                companyId : $scope.filter ? $scope.filter.companyId : null,
            }

        }).then(function (response) {
            $scope.allProjects = response.data
        });
    };

    $scope.cleanFilter = function() {
        $scope.filter = null;
        $scope.allProjects();
    }

    $scope.getProfiles = function(){
        $http({
            url: contextPath + '/api/v1/profiles/mini',
            method: 'GET'
        }).then(function (response) {
            $scope.allProfiles = response.data
        });
    }

    $scope.getCompanies = function(){
        $http({
            url: contextPath + '/api/v1/companies',
            method: 'GET'
        }).then(function (response) {
            $scope.allCompanies = response.data
        });
    }

    $scope.getProfiles();
    $scope.getCompanies();
    $scope.getProjects();

});
