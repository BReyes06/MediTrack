import { Link } from "react-router-dom";

export const Signup = () => {
  return (
    <section>
      <form>
        <h2>Create an account.</h2>
        <div>
          <label htmlFor="username" className="form-text">
            Username:&nbsp;
          </label>
          <input
            type="text"
            name="username"
            id="username"
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="password" className="form-text">
            Password:&nbsp;
          </label>
          <input
            type="password"
            name="password"
            id="password"
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="first" className="form-text">
            First Name:&nbsp;
          </label>
          <input type="text" name="first" id="first" className="form-control" />
        </div>
        <div>
          <label htmlFor="middle" className="form-text">
            Middle Name:&nbsp;
          </label>
          <input
            type="text"
            name="middle"
            id="middle"
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="last" className="form-text">
            Last Name:&nbsp;
          </label>
          <input type="text" name="last" id="last" className="form-control" />
        </div>
        <div>
          <label htmlFor="email" className="form-text">
            Email address:&nbsp;
          </label>
          <input
            type="email"
            name="email"
            id="email"
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="phone" className="form-text">
            Phone Number:&nbsp;
          </label>
          <input type="tel" name="phone" id="phone" className="form-control" />
        </div>
        <div className="d-flex justify-content-center mt-3">
          <button className="btn btn-danger mx-2">Submit</button>
          <Link to="/" className="btn btn-dark mx-2">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
