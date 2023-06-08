export const Home: React.FC = () => {
  return (
    <section className="home d-flex flex-column align-items-center">
      <h1>Welcome to MediTrack!</h1>
      <img src={require("../images/med.gif")} alt="loading..." />{" "}
    </section>
  );
};
