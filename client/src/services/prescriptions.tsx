export async function getMeds(drug: string) {
  const response = await fetch(
    `https://api.fda.gov/drug/ndc.json?search=generic_name:"${drug}"&limit=5`
  );
  const json = response.json();
  console.log(json);
}
