import React, {Component} from "react";
import TextInput from "./TextInput.jsx";

class NameInput extends Component {

  constructor() {
    super();
    this.state = {
      isEmpty: true,
      value: null,
      valid: false,
      errorMessage: "Name is invalid",
      errorVisible: false
    };
  }

  handleChange(_this) {
    return function (event) {
      _this.validation(event.target.value);
      if (_this.props.onChange) {
        _this.props.onChange(event);
      }
    }

  }

  validation(value) {
    let valid = true;
    let errorVisible = false;

    if (value.length <= 2) {
      valid = false;
      errorVisible = true;
    }

    this.setState({
      value: value,
      isEmpty: value.trim().length <= 0,
      valid: valid,
      errorVisible: errorVisible
    });
  }

  render() {
    return (
      <div className="form-elem">
        <label htmlFor="name">Name: </label>
        <input
          id="name"
          onChange={this.handleChange(this)}
          onTouchEnd={this.handleChange(this)}
          value={this.props.value}/>
        <TextInput visible={this.state.errorVisible && !this.state.valid} message={this.state.errorMessage}/>
      </div>
    )
  }

}

export default NameInput;