import FrontImg from "../images/frontpage.avif";

export const Home = () => {
  return (
    <section className="home d-flex flex-column align-items-center">
      <h1>MediTrack</h1>
      <h2>Your Health, On Track</h2>
      <img src={FrontImg} alt="Healthy people being healthy." />
    </section>
  );
};
