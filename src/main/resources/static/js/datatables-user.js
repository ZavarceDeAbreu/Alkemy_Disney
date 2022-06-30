// Call the dataTables jQuery plugin
$(document).ready(function() {
	uploadUser();
	$('#users').DataTable();
});

async function uploadUser() {

	const request = await fetch('api/users', {
		method: 'GET',
		headers: {
			'Accept': 'application/json',
			'Conten-Type': 'application/json'
		}
	});

	const users = await request.json();

	let listHtml = '';

	for(let user of users) {
		
		let buttonDelete = '<a href="#" onclick="deleteUser(' + user.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
		 let phone = user.phone == null ? '-' : user.phone;
		
		let userHtml = '<tr><td>' + user.id + '</td><td>' + user.name + ' ' + user.lastName + '</td><td>' 
		+ user.email + '</td><td>' + phone 
		+ '</td><td>' + buttonDelete + '</td></tr>';
		
		listHtml += userHtml;
	} 
	
	document.querySelector('#users tbody').outerHTML = listHtml;

}

async function deleteUser(id) {
	
	if(!confirm('¿Desea eliminar este usuario?')) {
		return;
	}
	
	const request = await fetch('api/users/' + id, {
		method: 'DELETE',
		headers: {
			'Accept': 'application/json',
			'Conten-Type': 'application/json'
		}
	});
	
	location.reload();
}
