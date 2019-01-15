window.onload = async () => {

	const url = 'blurchain.club1.fr';
	let lastFetchedBlock = {};
	let list = document.getElementById("list-blocks");
	const template = await fetch('block.mustache')
		.then(response => response.text());

	// Initially load the currents heads.
	const pocket = await fetch(`${url}/pocket.json`)
		.then(handleErrors)
		.catch(console.warn);

	lastFetchedBlock = pocket.heads[0];

	// Initially load the currents heads.
	await loadBlock(lastFetchedBlock.previousHash)
		.then(html => list.insertAdjacentHTML('afterbegin', html));

	// Detect when scrolled to bottom.
	document.addEventListener('scroll', async () => {
		console.log('ça scroll par içi');
		if (html.scrollTop < 5) {
			console.log('arrivé en bas');
			if (lastFetchedBlock.previousHash != "0") {
				console.log('chargement de nouveaux éléments');
				loadBlock(lastFetchedBlock.previousHash)
					.then(html => list.insertAdjacentHTML('beforebegin', html));
			}
		}
	});

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