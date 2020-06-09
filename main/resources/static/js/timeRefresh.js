var options = {
  day: 'numeric',
  hour: 'numeric',
  minute: 'numeric'
};
setInterval(() => {
	let date=new Date();
$("#time").text(date.toLocaleTimeString())}, 1000);