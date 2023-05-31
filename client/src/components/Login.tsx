export const Login = () => {
  return (
    <section>
      <form className="d-flex flex-column align-items-center">
        <h2>Enter your log in details.</h2>
        <div className="form-group">
          <label htmlFor="username">Username:&nbsp;</label>
          <input
            type="text"
            name="username"
            id="username"
            className="form-control"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:&nbsp;</label>
          <input
            type="password"
            name="password"
            id="password"
            className="form-control"
          />
        </div>
        <button className="btn btn-dark mt-3">Log In</button>
      </form>
    </section>
  );
};
