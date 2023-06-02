type ErrorsProps = {
  errors: Array<string>;
};

export const FormErrors = (props: ErrorsProps) => {
  if (!props.errors || props.errors.length === 0) {
    return null;
  }

  return (
    <>
      <div className="alert alert-danger mt-3 form-errors" role="alert">
        <h3>Errors</h3>
        <p>The following errors occurred:</p>
        <ul>
          {props.errors.map((error, index) => (
            <li key={`${index}-${error}`}>{error}</li>
          ))}
        </ul>
      </div>
    </>
  );
};
