window.onload = async () => {
	const url = 'https://blurchain.club1.fr';
	let lastFetchedBlock = {};
	let list = document.getElementById("phrases");
	const template = 
	`<li id="map-{{data.value}}">
		<h1>Data : {{data.value}}</h1>
		<h3>Hash Block :  {{hash}} </h3>
		<h4> Previous Hash : <i> {{previousHash}}</i> </h4> 
		<h4> Sender : {{data.sender}}</h4>
		<h4> Signature : {{data.signature}}</h4>
		<h4>TimeStamp : <time class="timeago" datetime="{{timeStamp}}">{{dateString}} {{timeStamp}}</time></h4>
		{{{html}}}
	</li>`; 	   
	// Initially load the currents heads.
	const pocket = await fetch(`${url}/pocket.json`)
		.then(handleErrors)
		.catch(console.warn);

	lastFetchedBlock = pocket.heads[0];
	await loadBlock(lastFetchedBlock.hash)
	.then(html => list.insertAdjacentHTML('afterbegin', html));
while (lastFetchedBlock.previousHash != 0){
	// Initially load the currents heads.
	await loadBlock(lastFetchedBlock.previousHash)
		.then(html => list.insertAdjacentHTML('afterbegin', html));
}
console.log(pocket); 

	async function loadBlock(hash) {
		return fetch(`${url}/blocks/${hash}.json`)
			.then(handleErrors)
			.then(block => {
				lastFetchedBlock = block;
				let html = Mustache.render(template, block);
				return html;
			})
			.catch(console.warn);
	}
}

async function handleErrors(response) {
	if (!response.ok) {
		const data = await response.json();
		throw Error(`${response.statusText}. ${data.message}`);
	}
	return response.json();
}