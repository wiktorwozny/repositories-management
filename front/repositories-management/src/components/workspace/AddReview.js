import { connect } from "react-redux";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  addReview,
  addWorkspace,
  deleteWorkspace,
  editWorkspace,
} from "../../store/slices/workspaceSlice";
import { v4 as uuidv4 } from "uuid";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextareaAutosize,
  TextField,
} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import EditIcon from "@mui/icons-material/Edit";
import RateReviewIcon from "@mui/icons-material/RateReview";

function AddReview(props) {
  const dispatch = useDispatch();
  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = async (formData) => {
    console.log("formData", formData);

    await dispatch(
      addReview({
        workspaceId: props.workspaceId,
        review: formData.review,
        repositoryId: props.repositoryId,
        pullRequestUrl: props.pullRequestUrl,
      }),
    );

    handleClose();
  };

  return (
    <React.Fragment>
      <IconButton onClick={handleOpen}>
        <RateReviewIcon />
      </IconButton>

      <Dialog
        open={open}
        fullWidth={true}
        maxWidth="sm"
        onClose={handleClose}
        PaperProps={{
          component: "form",
          onSubmit: async (event) => {
            event.preventDefault();
            const formData = new FormData(event.currentTarget);
            const formJson = Object.fromEntries(formData.entries());
            await handleSubmit(formJson);
          },
        }}
      >
        <DialogTitle>Submit a review for the pull request</DialogTitle>
        <DialogContent>
          <DialogContentText>{props.pullRequestUrl}</DialogContentText>
          <TextField
            autoFocus
            multiline={true}
            required
            margin="dense"
            id="review"
            name="review"
            label="Review"
            type="text"
            fullWidth
            variant="standard"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Submit</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}

export default connect()(AddReview);
