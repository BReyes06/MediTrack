import { useEffect, useState } from "react";

export const NotFound = () => {
  const [imageLink, setImageLink] = useState("");

  async function getDog() {
    const response = await fetch("https://api.thecatapi.com/v1/images/search");
    const json = await response.json();
    setImageLink(json[0].url);
  }

  useEffect(() => {
    getDog();
  }, []);

  return (
    <section
      className="alert alert-danger d-flex flex-column align-items-center notfound"
      role="alert"
    >
      <h1>Not Found</h1>
      <p>The route you're looking for does not exist on this website.</p>
      <p>The good side is we'll give you a random cat picture.</p>
      <img src={imageLink} alt="Random cat." />
    </section>
  );
};
