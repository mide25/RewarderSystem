var app = angular.module('rewarderApp', ['angularSpinner']);

app.controller('RewarderController', function ($scope, RewarderService, usSpinnerService) {

    $scope.uploading = false;

    $scope.vouchers = [];

    $scope.noEligibleCustomerMessage = "";

	$scope.uploadFile = function () {
        $scope.uploading = true;
        usSpinnerService.spin('upload-spinner');
        $scope.noEligibleCustomerMessage = "";
	    if(!$scope.isFileSelected()){
            usSpinnerService.stop('upload-spinner');
            $scope.uploading = false;
	        alertify.error("Please select a file!");
	        return;
        }
		console.log("uploading...");
        var formData = new FormData();
        formData.append("multipartFile", $("#uploader")[0].files[0]);
        $scope.vouchers = [];
        RewarderService.uploadFile(formData).then(
        	function (result) {
                usSpinnerService.stop('upload-spinner');
                $scope.uploading = false;
                $scope.vouchers = result.data;
                if($scope.vouchers.length == 0){
                    $scope.noEligibleCustomerMessage = "No Customers are eligible for Vouchers today!";
                }
			}, function (reason) {
                usSpinnerService.stop('upload-spinner');
                $scope.uploading = false;
                $scope.noEligibleCustomerMessage = "";
        	    alertify.error("An error occurred: " + reason.data.message);
			}
		);
    };

    $scope.isFileSelected = function () {
		var file = $("#uploader")[0].files[0];
		if(file){
			return true
		}
		return false;
    }
	
});


app.factory("RewarderService", function($http){
	var obj = this;
	obj.uploadFile = function (data) {
        var req = {
            headers: {'Content-Type': undefined},
            method: "POST",
            data: data,
            url: "/rewarder/get-vouchers/today"
        };

        return $http(req);
    };
	
	return obj;
});