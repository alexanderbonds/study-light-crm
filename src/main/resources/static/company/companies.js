angular.module('app').controller('companyController', function ($scope, $rootScope, $http, $mdDialog) {
    const contextPath = 'http://localhost:8180/app';

    function dialogController($scope, $rootScope, $mdDialog) {
        $scope.responsibleManagers = $rootScope.managers;

        $scope.submitNewCompany = function () {
            $http.post(contextPath + '/api/v1/companies/', $scope.newCompany)
                .then(function successCallback(response) {
                    $scope.newCompany = null;
                    $rootScope.showAllCompanies();
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

    $rootScope.showAllCompanies = function () {
        $scope.showCompanyInfo = false;
        $scope.showCompanyContact = false;
        $scope.showAllCompaniesInfo = true;
        $http({
            url: contextPath + '/api/v1/companies',
            method: 'GET'
        }).then(function (response) {
            $scope.companies = response.data;
        });
    };

    $scope.showNewCompanyForm = function (ev) {
        $mdDialog.show({
            templateUrl: 'company/createCompany.html',
            controller: dialogController,
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen,
        });
    };

    $scope.loadManagers = function() {
        $http({
            url: contextPath + '/api/v1/profiles/mini',
            method: 'GET'
        }).then(function (response) {
            $rootScope.managers = response.data;
        });
    };

    $scope.deleteCompany = function (companyId) {
        $http({
            url: contextPath + '/api/v1/companies/' + companyId,
            method: 'DELETE'
        }).then(function (response) {
            $scope.companyIdToDelete = null;
            $('#deleteCompanyModal').modal('hide');
            $scope.showAllCompanies();
        });
    };

    $scope.showCompany = function (companyId) {
        $scope.showAllCompaniesInfo = false;
        $scope.showCompanyContact = false;
        $scope.showCompanyInfo = true;
        $http({
            url: contextPath + '/api/v1/companies/' + companyId,
            method: 'GET'
        }).then(function (response) {
            $scope.companyInfo = response.data;
        });
    };

    $scope.showContact = function (contact) {
        $scope.showCompanyInfo = false;
        $scope.showCompanyContact = true;
        $scope.contactInfo = contact;
    };

    $scope.updateContact = function () {
        $scope.contactInfo.companyId = $scope.companyInfo.id;
        $http.put(contextPath + '/api/v1/companies/contact', $scope.contactInfo)
            .then(function (response) {
                $scope.contactInfo = null;
                $scope.showCompany($scope.companyInfo.id);
        });
    };

    $scope.deleteContact = function (id) {
        $http({
            url: contextPath + '/api/v1/companies/contact/' + id,
            method: 'DELETE'
        })
            .then(function (response) {
                $scope.contactIdToDelete = null;
                $('#deleteContactModal').modal('hide');
                $scope.showCompany($scope.companyInfo.id);
            });
    };

    $scope.tryToDeleteCompany = function (company) {
        if (company.tasksCount !== 0) {
            // alert("Нельзя удалить компанию с привязанными задачами!");
            $('#unableToDeleteModal').modal('show');
        } else {
            $scope.companyIdToDelete = company.id;
            $('#deleteCompanyModal').modal('show');
        }
    }

    $scope.markContactToDelete = function (id) {
        $scope.contactIdToDelete = id;
    }

    $scope.showAllCompanies();

    $scope.loadManagers();
});
